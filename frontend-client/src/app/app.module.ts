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
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { LoginFormComponent } from './login-form/login-form.component';
@NgModule({
   declarations: [
      AppComponent,
      NavigationBarComponent,
      FooterComponent,
      SearchFormComponent,
      SearchResultsComponent,
      RegisterComponent,
      LoginFormComponent
   ],
   imports: [
      NgMultiSelectDropDownModule.forRoot(),
      BrowserModule,
      NgbModule,
      AppRoutingModule,
      AngularFontAwesomeModule,
      BrowserModule,
      ReactiveFormsModule,
      FormsModule
   ],
   exports: [
      NavigationBarComponent,
      FooterComponent
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ],
   entryComponents: [
      RegisterComponent,
      LoginFormComponent
   ]
})
export class AppModule { }
