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

  commentsList: Comment[] = [];

  currentComment: Comment;
  collectionSize = 0;
  page = 1;
  pageSize = 10;

  ngOnInit() {
     this.getComments();
  }

  // get all comments
  getComments() {
    this.commentsService.getComments(this.page - 1, this.pageSize).subscribe(
      data => {
        this.commentsList = data['content'];
        this.collectionSize = data['totalElements'];
        this.pageSize = data['pageable'].pageSize;
      },
      error => {
        alert('An error occurred while getting comments.');
      }
    );
  }

  // approve/reject comment
  editComment(comment: Comment, bool: boolean) {
    this.commentsService.approveComment(comment, bool)
    .subscribe(() => {
      if (bool === true) {
        comment.commentState = 'PUBLISHED';
      } else {
        comment.commentState = 'UNPUBLISHED';
      }
    });
  }

  // open modal dialog with the chosen comment
  open(content, comment: Comment) {
    this.currentComment = comment;
    this.modalService.open(content);
  }

  onPageChange(pageNo) {
    this.page = pageNo;
    this.getComments();
  }
}
