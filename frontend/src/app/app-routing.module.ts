import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './views/login/login.component';
import {RegistrationComponent} from './views/registration/registration.component';
import {TestsComponent} from './views/tests/tests.component';
import {TestFormComponent} from './views/test-form/test-form.component';
import {AuthGuard} from './auth.guard';
import {PositionsComponent} from './views/positions/positions.component';
import {PositionFormComponent} from './views/position-form/position-form.component';

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'tests', component: TestsComponent},
  {path: 'tests/:id', component: TestFormComponent},
  {path: 'positions', component: PositionsComponent},
  {path: 'positions/:id', component: PositionFormComponent}
];

for (const route of routes) {
  route.canActivate = [AuthGuard];
}

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
