import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MustMatch, SignUpComponent} from './sign-up.component';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SignUpComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;
  const formBuilder: FormBuilder = new FormBuilder();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule
      ],
      declarations: [
        SignUpComponent
      ],
      providers: [
        {provide: FormBuilder, useValue: formBuilder}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpComponent);
    component = fixture.componentInstance;

    component.userForm = formBuilder.group({
      id: ['', [
        Validators.required,
        Validators.minLength(6)
      ]],
      password: ['', [
        Validators.required,
        Validators.pattern(/^(?=.*[a-zA-Z])(?=.*[!@#$%^~*+=-])(?=.*[0-9]).{8,20}/)
      ]],
      confirmPassword: ['']
    }, {validator: MustMatch('password', 'confirmPassword')});

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
