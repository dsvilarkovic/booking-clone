import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FooterComponent } from './footer/footer.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { SearchFormComponent } from './search/search-form/search-form.component';
import { SearchResultsComponent } from './search/search-results/search-results.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { RegisterComponent } from './register/register.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginFormComponent } from './login-form/login-form.component';
import { ChatboxComponent } from './chatbox/chatbox.component';
import { DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AccommodationProfileComponent } from './accommodation-profile/accommodation-profile.component';
import { AgmCoreModule } from '@agm/core';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ReservationCheckoutComponent } from './reservation-checkout/reservation-checkout.component';

@NgModule({
   declarations: [
      AppComponent,
      NavigationBarComponent,
      FooterComponent,
      SearchFormComponent,
      SearchResultsComponent,
      RegisterComponent,
      LoginFormComponent,
      ChatboxComponent,
      AccommodationProfileComponent,
      UserProfileComponent,
      ReservationCheckoutComponent
   ],
   imports: [
      NgMultiSelectDropDownModule.forRoot(),
      BrowserModule,
      NgbModule,
      AppRoutingModule,
      AngularFontAwesomeModule,
      BrowserModule,
      ReactiveFormsModule,
      FormsModule,
      HttpClientModule,
      AgmCoreModule.forRoot({
        apiKey: ''
      })
   ],
   exports: [
      NavigationBarComponent,
      FooterComponent
   ],
   providers: [ DatePipe ],
   bootstrap: [
      AppComponent
   ],
   entryComponents: [
      RegisterComponent,
      LoginFormComponent
   ]
})
export class AppModule { }
