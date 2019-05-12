import { Injectable } from '@angular/core';
import {MainEntity} from "../model/entities";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export abstract class BaseEntityService {

  protected constructor(protected http: HttpClient) { }

  abstract getEntityUrl(): string

  getAll<E extends MainEntity>(): Observable<E[]> {
    return this.http.get<E[]>(this.getEntityUrl())
  }

  getOne<E extends MainEntity>(id: number): Observable<E> {
    return this.http.get<E>(`${this.getEntityUrl()}/${id}`)
  }

  create<E extends MainEntity>(entity: E): Observable<E> {
    return this.http.post<E>(this.getEntityUrl(), entity)
  }

  update<E extends MainEntity>(entity: E): Observable<void> {
    return this.http.put<void>(`${this.getEntityUrl()}/${entity.id}`, entity)
  }

  delete<E extends MainEntity>(entity: E): Observable<void> {
    return this.http.delete<void>(`${this.getEntityUrl()}/${entity.id}`)
  }

}
