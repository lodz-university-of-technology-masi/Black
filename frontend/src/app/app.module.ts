import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './views/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {LoginHeaderComponent} from './components/login-header/login-header.component';
import {RegistrationComponent} from './views/registration/registration.component';
import {RegisterHeaderComponent} from './components/register-header/register-header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {APIInterceptor} from './services/interceptors/api-interceptor';
import {TestsComponent} from './views/tests/tests.component';
import {TestFormComponent} from './views/test-form/test-form.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {PositionsComponent} from './views/positions/positions.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { PositionFormComponent } from './views/position-form/position-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginHeaderComponent,
    RegistrationComponent,
    RegisterHeaderComponent,
    FooterComponent,
    TestsComponent,
    TestFormComponent,
    PositionsComponent,
    HeaderComponent,
    PositionFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    ToastrModule.forRoot()
  ],
  providers: [
    {
      provide: 'BASE_API_URL',
      useValue: '/api'
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: APIInterceptor,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
