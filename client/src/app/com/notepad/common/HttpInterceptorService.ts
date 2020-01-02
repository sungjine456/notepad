import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {catchError} from 'rxjs/operators';
import {Router} from "@angular/router";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = sessionStorage.getItem("token");
    let cloneReq = req;

    if (token) {
      cloneReq = req.clone({
        headers: req.headers.set("X-Auth-Token", token)
      });
    }

    return next.handle(cloneReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404 && !error.url.includes("/user/")) {
          this.router.navigateByUrl("/");
        }

        return throwError(error);
      })
    )
  }
}
