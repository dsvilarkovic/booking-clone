import { CommentsService} from './comments.service';
import { Comment } from './comment';
import { Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  constructor(private commentsService: CommentsService,
              private modalService: NgbModal,
              datePipe: DatePipe) {}

  // commentsList: any;
  // za sada primer
  commentsList: Comment[] = [
    {
      id: 0, value: 'Neki komentar.', username: 'majak96', administrator: null, date: new Date('2019-04-29 11:13:00')
    },
    {
      id: 1, value: 'Opet neki komentar.', username: 'vesnamilic', administrator: 'admin', date: new Date('2019-04-29 10:43:00')
    },
    {
      id: 2, value: 'Neki treci komentar.', username: 'dsvilarkovic', administrator: null, date: new Date('2019-04-28 15:23:00')
    },
    {
      id: 3, value: 'Komentaaaaaaaaaaaaaaar.', username: 'borisjovanovic', administrator: 'admin', date: new Date('2019-04-27 13:13:00')
    },
  ];

  currentComment: Comment;
  collectionSize = 3;
  page = 1;
  pageSize = 7;

  ngOnInit() {
     this.getComments();
  }

    // get all comments
    getComments() {
      /* this.commentsService.getComments().subscribe(
        data => {
          this.commentsList = data;
        },
        error => {
          console.log('There was an error.');
        }
      ); */
    }

    // approve/reject comment
    editComment(comment: Comment) {
      comment.administrator = (comment.administrator == null ? 'admin' : null);

      /* this.commentsService.approveComment(comment).subscribe(
        data => {
          // do nothing for now
        },
        error => {
          if (comment.administrator != null ) {
            alert('There was an error.');
            comment.administrator = null;
          } else {
            alert('There was an error.');
            comment.administrator = 'admin';
          }
        }
      ); */
    }

    // open modal dialog with the chosen comment
    open(content, comment: Comment) {
      this.currentComment = comment;

      this.modalService.open(content);
    }

}
