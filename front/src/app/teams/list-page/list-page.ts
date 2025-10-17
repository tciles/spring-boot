import {Component, inject, OnInit, signal} from '@angular/core';
import {ApiService, Team} from '../../core/services/api.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-list-page',
  imports: [
    RouterLink
  ],
  templateUrl: './list-page.html',
  styleUrl: './list-page.css',
  standalone: true
})
export class ListPage implements OnInit  {
  teams = signal<Team[]>([]);
  private apiService = inject(ApiService);

  public ngOnInit(): void {
    this.loadTeams();
  }

  private loadTeams() {
    this.apiService.getTeam().subscribe({
      next: (teams) => {
        this.teams.set(teams);
      },
      error: (err) => {
        this.teams.set([]);
      }
    });
  }

  deleteTeam(team: Team) {
    this.apiService.deleteTeam(team.id).subscribe({
      next: () => {this.loadTeams();},
      error: (err) => { this.teams.set([]); },
      complete: () => {this.loadTeams();}
    })
  }
}
