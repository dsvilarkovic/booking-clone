import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchResultsComponent } from './search/search-results/search-results.component';
import { SearchFormComponent } from './search/search-form/search-form.component';

const routes: Routes = [
  {
    path: '',
    component: SearchFormComponent
  },
  {
    path: 'searchResults',
    component: SearchResultsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

