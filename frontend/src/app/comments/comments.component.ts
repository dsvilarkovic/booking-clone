import { CommentsService, Comment } from './comments.service';
import { Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  constructor(private commentsService: CommentsService,
              private modalService: NgbModal) { }

  commentsList: any;
  currentComment: Comment;
  collectionSize = 3;
  page = 1;
  pageSize = 7;

  ngOnInit() {
    this.getComments();
  }

    // get all comments
    getComments() {
      this.commentsService.getComments().subscribe(
        data => {
          this.commentsList = data;
        },
        error => {
          console.log('There was an error.');
        }
      );
    }

    // approve/reject comment
    editComment(comment: Comment) {
      comment.administrator = (comment.administrator == null ? 'admin' : null);

      this.commentsService.approveComment(comment).subscribe(
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
      );
    }

    // open modal dialog with the chosen comment
    open(content, comment: Comment) {
      this.currentComment = comment;

      this.modalService.open(content);
    }

}
