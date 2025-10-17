import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';

type Payload<T> = {
  data: T,
  message: string,
  status: number
}

export type Team = {
  id: number,
  name: string
}

type PlayerApi = {
  id: number,
  first_name: string,
  last_name: string,
  email: string|null
}

export type Player = {
  id: number,
  firstname: string,
  lastname: string,
  email: string|null
}

const sortById = <T extends {id: number}>(a: T, b: T) => {
  return a.id - b.id;
}

@Injectable({
  providedIn: "root"
})
export class ApiService {

  private static BASE_URL = "http://localhost:8080/api"

  public constructor(private httpClient: HttpClient) {
  }

  public getTeam(): Observable<Team[]> {
    return this.httpClient
      .get<Payload<Team[]>>(`${ApiService.BASE_URL}/teams/`)
      .pipe(map((payload) => payload.data.sort(sortById)));
  }

  public getTeamById(id: number): Observable<Team> {
    return this.httpClient
      .get<Payload<Team>>(`${ApiService.BASE_URL}/teams/${id}/view`)
      .pipe(map((payload) => payload.data));
  }

  createTeam(name: string) {
    return this.httpClient
      .post<Payload<Team>>(`${ApiService.BASE_URL}/teams`, { name })
      .pipe(map((payload) => payload.data));
  }

  deleteTeam(id: number) {
    return this.httpClient
      .delete<Payload<null>>(`${ApiService.BASE_URL}/teams/${id}`)
      .pipe(map(() => null));
  }

  getPlayersByTeamName(name: string) {
    return this.httpClient
      .get<Payload<PlayerApi[]>>(`${ApiService.BASE_URL}/players?team=${name}`)
      .pipe(map((payload): Player[] => payload.data.sort(sortById).map(item => {
        return {
          id: item.id,
          firstname: item.first_name,
          lastname: item.last_name,
          email: item.email
        };
      })));
  }

  createPlayer(firstname: string, lastname: string, email: string|null, teamId: number) {
    return this.httpClient
      .post<Payload<Player>>(`${ApiService.BASE_URL}/players`, {
        first_name: firstname,
        last_name: lastname,
        email,
        team_id: teamId
      })
      .pipe(map((payload) => payload.data));
  }

  deletePlayer(id: number) {
    return this.httpClient
      .delete<Payload<null>>(`${ApiService.BASE_URL}/players/${id}`)
      .pipe(map(() => null));
  }
}

