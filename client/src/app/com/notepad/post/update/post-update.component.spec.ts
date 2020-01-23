import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {PostUpdateComponent} from "./post-update.component";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('PostUpdateComponent', () => {
  let component: PostUpdateComponent;
  let fixture: ComponentFixture<PostUpdateComponent>;
  const formBuilder: FormBuilder = new FormBuilder();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule
      ],
      declarations: [PostUpdateComponent],
      providers: [
        {provide: FormBuilder, useValue: formBuilder}
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
