import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { FooterComponent } from './footer/footer.component';
import { CommentsComponent } from './comments/comments.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CodebookComponent} from './codebook/codebook.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { UserComponent } from './user/user.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { DatePipe } from '@angular/common';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AuthInterceptor } from './auth-interceptor';

@NgModule({
   declarations: [
      AppComponent,
      NavigationBarComponent,
      FooterComponent,
      CommentsComponent,
      CodebookComponent,
      UserComponent,
      LoginFormComponent
   ],
   imports: [
      NgbModule,
      HttpClientModule,
      BrowserModule,
      AppRoutingModule,
      ReactiveFormsModule,
      FormsModule,
      AngularFontAwesomeModule
   ],
   exports: [
      NavigationBarComponent,
      FooterComponent
   ],
   providers: [ DatePipe, { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true, }
   ],
   bootstrap: [
      AppComponent
   ],
   entryComponents: [
      LoginFormComponent
   ]
})
export class AppModule { }
