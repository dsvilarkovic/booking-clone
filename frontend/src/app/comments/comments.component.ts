import { CommentsService } from './comments.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  constructor(private commentsService: CommentsService) { }

  commentsList: any;

  ngOnInit() {
    this.getComments();
  }

    // ucitavanje komentara
    getComments() {
      this.commentsService.getComments().subscribe(
        data => {
          this.commentsList = data;
        },
        error => {
          console.log('Doslo je do greske prilikom ucitavanja komentara.');
        }
      );
    }

}
