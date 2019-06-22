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

  commentsList: Comment[] = [
  ];

  currentComment: Comment;
  // collectionSize = 7;
  collectionSize = 0;
  page = 1;
  pageSize = 10;

  ngOnInit() {
     this.getComments();
  }

    // get all comments
    getComments() {
      this.commentsList = null;
      this.commentsService.getComments(this.page, this.pageSize).subscribe(
        data => {
          console.log(data);
          this.commentsList = data['content'];
          this.collectionSize = data['totalElements'];
        },
        error => {
          console.log('There was an error.');
        }
      );
    }

    // approve/reject comment
    editComment(comment: Comment) {
      console.log('Alo');
      switch (comment.commentState) {
        case('NOT_REVIEWED'):
          this.commentsService.approveComment(comment, true)
          .subscribe(() => {
            comment.commentState = 'PUBLISHED';
          });
          break;
        case('PUBLISHED'):
          this.commentsService.approveComment(comment, false)
          .subscribe(() => {
            comment.commentState = 'UNPUBLISHED';
          });
          break;
        case('UNPUBLISHED'):
          this.commentsService.approveComment(comment, true)
          .subscribe(() => {
            comment.commentState = 'PUBLISHED';
          });
          break;
      }
      // this.getComments();
    }

    // open modal dialog with the chosen comment
    open(content, comment: Comment) {
      this.currentComment = comment;
      this.modalService.open(content);
    }

    onPageChange(pageNo) {
      this.getComments();
    }
}
