import { Injectable } from '@angular/core';
import {HttpClient, HttpRequest, HttpEvent} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class FileUploadService {

  constructor(private http: HttpClient) { }

  // file from event.target.files[0]
    uploadFile(url: string, file: File): Observable<HttpEvent<any>> {

    const formData = new FormData();
    formData.append('upload', file);
    const options = {
      reportProgress: true,
    };

    //   return this.http.post(url, formData, {observe: 'response'});
    const req = new HttpRequest('POST', url, formData, options);
    return this.http.request(req);
  }
}
