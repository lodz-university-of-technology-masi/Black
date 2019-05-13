import { Injectable } from '@angular/core';
import {MainEntity} from "../model/entities";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export abstract class BaseEntityService<E extends MainEntity> {

  protected constructor(protected http: HttpClient) { }

  abstract getEntityUrl(): string

  getAll(): Observable<E[]> {
    return this.http.get<E[]>(this.getEntityUrl())
  }

  getOne(id: number): Observable<E> {
    return this.http.get<E>(`${this.getEntityUrl()}/${id}`)
  }

  create(entity: E): Observable<E> {
    return this.http.post<E>(this.getEntityUrl(), entity)
  }

  update(entity: E): Observable<void> {
    return this.http.put<void>(`${this.getEntityUrl()}/${entity.id}`, entity)
  }

  delete(entity: E): Observable<void> {
    return this.http.delete<void>(`${this.getEntityUrl()}/${entity.id}`)
  }

}
