const pg = require('pg');

const pgConfig = {
	user: 'postgres',
	host: 'postgres.cgvdhu4dgw3f.us-east-2.rds.amazonaws.com',
	database: 'postgres',
	password: 'dYdqjGE2yJpCeq7',
	port: '5432'
}

let pgPool;

require('@google/cloud-debug');
exports.rating = function rating(request, response){
  
	if(!pgPool){
		pgPool = new pg.Pool(pgConfig);
	}

	//method must be post to create rating
	if(request.method == 'POST'){
		rating_value = request.body.rating;
		reservation_id = request.body.reservation;
		comment_value = request.body.comment;

    	//rating value and reservation id must exist
    	if(rating_value == null || comment_value == null || comment_value == "" || reservation_id == null || rating_value < 1 || rating_value > 5){
    		response.status(400).send("Bad request.");
    	}
    	else{

			pgPool.connect()
				.then(client => {
					return client.query('SELECT EXISTS (SELECT * FROM reservation WHERE id = $1)', [reservation_id])
					.then(res => {
						client.release();

						//reservation does not exist
						if(res.rows[0].exists != true){
							response.status(400).send("Reservation with the id " + reservation_id + " does not exist.");
						}
						else{
							pgPool.connect()
								.then(client => {
									return client.query('INSERT INTO rating (value) VALUES ($1) RETURNING id', [rating_value])
									.then(res => {
										client.release();	
										rating_id = res.rows[0].id;			
										
										pgPool.connect()
											.then(client => {
												return client.query('UPDATE reservation SET rating_id = $1 WHERE id = $2', [rating_id, reservation_id])
												.then(res => {
													client.release();													
												})
												.catch(e => {
													client.release();
													response.status(500).send(e);
												})
											})
									})
									.catch(e => {
										client.release();
										response.status(500).send(e);
									})
								})
							
							pgPool.connect()
							.then(client => {
								return client.query('INSERT INTO comment (comment_state, date, deleted, value) VALUES ($1, $2, $3, $4) RETURNING id', ["NOT_REVIEWED", (new Date()).getTime(), false, comment_value])
								.then(res => {
									client.release();
									comment_id = res.rows[0].id;

									pgPool.connect()
										.then(client => {
											return client.query('UPDATE reservation SET comment_id = $1 WHERE id = $2', [comment_id, reservation_id])
											.then(res => {
												client.release();													
											})
											.catch(e => {
												client.release();
												response.status(500).send(e);
											})
										})
								})
								.catch(e => {
									client.release();
									response.status(500).send(e);
								})
							})

							response.status(200).send("Successfully added rating and comment.");
						}
					})
					.catch(e => {
						client.release();
						response.status(500).send("Internal server error.");
					})
				})

		}

	}
	else{
		response.status(404).send("Not found.");
	}

    

	
  
    

};