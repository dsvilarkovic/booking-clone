import { CommentsComponent } from './comments/comments.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CodebookComponent } from './codebook/codebook.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  {
    path: 'comments',
    component: CommentsComponent
  },
  {
    path: 'codebooks',
    component: CodebookComponent
  },
  {
    path: 'users',
    component: UserComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
