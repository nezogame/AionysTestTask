/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { deleteById } from '../fn/note/delete-by-id';
import { DeleteById$Params } from '../fn/note/delete-by-id';
import { getAll } from '../fn/note/get-all';
import { GetAll$Params } from '../fn/note/get-all';
import { getById } from '../fn/note/get-by-id';
import { GetById$Params } from '../fn/note/get-by-id';
import { NoteDto } from '../models/note-dto';
import { save } from '../fn/note/save';
import { Save$Params } from '../fn/note/save';
import { update } from '../fn/note/update';
import { Update$Params } from '../fn/note/update';

@Injectable({ providedIn: 'root' })
export class NoteService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getById()` */
  static readonly GetByIdPath = '/notes/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getById$Response(params: GetById$Params, context?: HttpContext): Observable<StrictHttpResponse<NoteDto>> {
    return getById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getById(params: GetById$Params, context?: HttpContext): Observable<NoteDto> {
    return this.getById$Response(params, context).pipe(
      map((r: StrictHttpResponse<NoteDto>): NoteDto => r.body)
    );
  }

  /** Path part for operation `update()` */
  static readonly UpdatePath = '/notes/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `update()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  update$Response(params: Update$Params, context?: HttpContext): Observable<StrictHttpResponse<NoteDto>> {
    return update(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `update$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  update(params: Update$Params, context?: HttpContext): Observable<NoteDto> {
    return this.update$Response(params, context).pipe(
      map((r: StrictHttpResponse<NoteDto>): NoteDto => r.body)
    );
  }

  /** Path part for operation `deleteById()` */
  static readonly DeleteByIdPath = '/notes/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteById$Response(params: DeleteById$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return deleteById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteById(params: DeleteById$Params, context?: HttpContext): Observable<void> {
    return this.deleteById$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `getAll()` */
  static readonly GetAllPath = '/notes';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAll()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAll$Response(params?: GetAll$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<NoteDto>>> {
    return getAll(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAll$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAll(params?: GetAll$Params, context?: HttpContext): Observable<Array<NoteDto>> {
    return this.getAll$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<NoteDto>>): Array<NoteDto> => r.body)
    );
  }

  /** Path part for operation `save()` */
  static readonly SavePath = '/notes';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `save()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  save$Response(params: Save$Params, context?: HttpContext): Observable<StrictHttpResponse<NoteDto>> {
    return save(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `save$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  save(params: Save$Params, context?: HttpContext): Observable<NoteDto> {
    return this.save$Response(params, context).pipe(
      map((r: StrictHttpResponse<NoteDto>): NoteDto => r.body)
    );
  }

}
