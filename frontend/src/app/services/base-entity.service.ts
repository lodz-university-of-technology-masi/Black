import { Injectable } from '@angular/core';
import {MainEntity} from '../model/entities';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export abstract class BaseEntityService<E extends MainEntity> {

  protected constructor(protected http: HttpClient) { }

  abstract getEntityUrl(): string;


  getAll(): Promise<E[]> {
    return this.http.get<E[]>(this.getEntityUrl()).toPromise()
  }

  getOne(id: number): Promise<E> {
    return this.http.get<E>(`${this.getEntityUrl()}/${id}`).toPromise()
  }

  create(entity: E): Promise<E> {
    return this.http.post<E>(this.getEntityUrl(), entity).toPromise()
  }

  update(entity: E): Promise<void> {
    return this.http.put<void>(`${this.getEntityUrl()}/${entity.id}`, entity).toPromise()
  }

  delete(entity: E): Promise<void> {
    return this.http.delete<void>(`${this.getEntityUrl()}/${entity.id}`).toPromise()
  }

}
