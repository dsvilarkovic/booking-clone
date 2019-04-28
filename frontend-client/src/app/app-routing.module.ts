import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchResultsComponent } from './search/search-results/search-results.component';
import { SearchFormComponent } from './search/search-form/search-form.component';
import { ChatboxComponent } from './chatbox/chatbox.component';

const routes: Routes = [
  {
    path: '',
    component: SearchFormComponent
  },
  {
    path: 'searchResults',
    component: SearchResultsComponent
  },
  {
    path: 'chat',
    component: ChatboxComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

