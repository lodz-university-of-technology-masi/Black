import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './views/login/login.component';
import {RegistrationComponent} from './views/registration/registration.component';
import {TestsComponent} from "./views/tests/tests.component";
import {TestFormComponent} from "./views/test-form/test-form.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'tests', component: TestsComponent},
  {path: 'tests/:id', component: TestFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
