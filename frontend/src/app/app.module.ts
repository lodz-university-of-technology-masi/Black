import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';

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
import {PositionsComponent} from './views/positions/positions.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DeviceDetectorModule} from "ngx-device-detector";
import {GlobalErrorHandler} from "./global-error-handler";
import {HeaderComponent} from './components/header/header.component';
import {PositionFormComponent} from './views/position-form/position-form.component';
import {RedactorsComponent} from './views/redactors/redactors.component';
import {RedactorFormComponent} from './views/redactor-form/redactor-form.component';
import {ContextMenuModule} from 'ngx-contextmenu';
import {SolveTestFormComponent} from './views/solve-test-form/solve-test-form.component';
import {NgxBootstrapSliderModule} from "ngx-bootstrap-slider";
import {AnswersComponent} from "./views/answers/answers.component";
import {EvaluationFormComponent} from "./views/evaluation-form/evaluation-form.component";
import {EvaluationsComponent} from "./views/evaluations/evaluations.component";
import {PermissionsFormComponent} from './views/permisions-form/permissions-form.component';
import {FileUploadModule} from "ng2-file-upload";

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
    PositionFormComponent,
    RedactorsComponent,
    RedactorFormComponent,
    SolveTestFormComponent,
    AnswersComponent,
    EvaluationFormComponent,
    EvaluationsComponent,
    SolveTestFormComponent,
    PermissionsFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    ContextMenuModule.forRoot({
      useBootstrap4: true,
    }),
    DeviceDetectorModule.forRoot(),
    NgxBootstrapSliderModule,
    FileUploadModule
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
    },
    {
      provide: ErrorHandler,
      useClass: GlobalErrorHandler
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
