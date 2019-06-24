import { AccommodationProfileComponent } from './accommodation-profile/accommodation-profile.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchResultsComponent } from './search/search-results/search-results.component';
import { SearchFormComponent } from './search/search-form/search-form.component';
import { ChatboxComponent } from './chatbox/chatbox.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ReservationCheckoutComponent } from './reservation-checkout/reservation-checkout.component';

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
  },
  {
    path: 'accommodation/:id',
    component: AccommodationProfileComponent
  },
  {
    path: 'user',
    component: UserProfileComponent
  },
  // TODO: obrisati ovaj primer, samo za testiranje sluzi
  {
    path: 'reservation-checkout',
    component: ReservationCheckoutComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

