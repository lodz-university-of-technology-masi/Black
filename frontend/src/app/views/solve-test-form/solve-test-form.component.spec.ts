import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SolveTestFormComponent } from './solve-test-form.component';

describe('SolveTestFormComponent', () => {
  let component: SolveTestFormComponent;
  let fixture: ComponentFixture<SolveTestFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SolveTestFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SolveTestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
