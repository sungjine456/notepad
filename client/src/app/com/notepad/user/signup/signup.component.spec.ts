import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SignupComponent} from './signup.component';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;
  const formBuilder: FormBuilder = new FormBuilder();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule
      ],
      declarations: [
        SignupComponent
      ],
      providers: [
        {provide: FormBuilder, useValue: formBuilder}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;

    component.userForm = formBuilder.group({
      id: ['', [
        Validators.required,
        Validators.minLength(6)
      ]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-zA-Z])(?=.*[!@#$%^~*+=-])(?=.*[0-9]).{8,20}/)
      ]]
    });

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should form invalid when empty', () => {
    expect(component.userForm.valid).toBeFalsy();
  });

  it('should check the validity of the id field', () => {
    let id = component.userForm.controls['id'];
    expect(id.valid).toBeFalsy();

    id.setValue("");
    expect(id.hasError('required')).toBeTruthy();

    id.setValue("a");
    expect(id.hasError('minlength')).toBeTruthy();

    id.setValue("newUserId");
    expect(id.errors).toBeNull();
  });

  it('should check the validity of the password field', () => {
    let password = component.userForm.controls['password'];
    expect(password.valid).toBeFalsy();

    password.setValue("");
    expect(password.hasError('required')).toBeTruthy();

    password.setValue("a");
    expect(password.hasError('pattern')).toBeTruthy();

    password.setValue("abcd1234");
    expect(password.hasError('pattern')).toBeTruthy();

    password.setValue("!@#$1234");
    expect(password.hasError('pattern')).toBeTruthy();

    password.setValue("pass123!");
    expect(password.errors).toBeNull();
  });
});
