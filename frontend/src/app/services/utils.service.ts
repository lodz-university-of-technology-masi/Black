import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private static SYNONYMS_URL = "synonyms";
  static FILES_URL = "files";

  constructor(private http: HttpClient) {
  }

  public async findSynonyms(phrase: string): Promise<string[]> {
    let opts ={
      params: {
        word: phrase
      }
    };
    return this.http.get<string[]>(UtilsService.SYNONYMS_URL, opts).toPromise()
  }
}
