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
    editComment(comment: Comment, bool: boolean) {
      this.commentsService.approveComment(comment, bool)
      .subscribe(() => {
        if (bool === true) {
          comment.commentState = 'PUBLISHED';
          console.log(comment.commentState);
        } else {
          comment.commentState = 'UNPUBLISHED';
          console.log(comment.commentState);
        }
      });
      
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
