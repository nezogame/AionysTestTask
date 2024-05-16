/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { NoteDto } from '../../models/note-dto';
import { NoteDtoRequest } from '../../models/note-dto-request';

export interface Save$Params {
      body: NoteDtoRequest
}

export function save(http: HttpClient, rootUrl: string, params: Save$Params, context?: HttpContext): Observable<StrictHttpResponse<NoteDto>> {
  const rb = new RequestBuilder(rootUrl, save.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<NoteDto>;
    })
  );
}

save.PATH = '/notes';
