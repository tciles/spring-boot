import {Component, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {ApiService, Player, Team} from '../../core/services/api.service';

@Component({
  selector: 'app-view-page',
  imports: [
    RouterLink
  ],
  templateUrl: './view-page.html',
  styleUrl: './view-page.css'
})
export class ViewPage implements OnInit {
  team = signal<Team | null>(null);
  players = signal<Player[]>([]);

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private apiService = inject(ApiService);

  ngOnInit(): void {
    this.route.paramMap.subscribe((paramMap) => {
      const teamId = paramMap.get("id")!;

      this.apiService.getTeamById(Number.parseInt(teamId)).subscribe({
        next: (team) => {
          this.team.set(team);
          this.loadPlayers(team);
        },
        error: async () => {
          await this.router.navigate([""]);
        }
      })
    });
  }

  private loadPlayers(team: Team) {
    this.apiService.getPlayersByTeamName(team.name).subscribe({
      next: (players) => {
        this.players.set(players);
      },
      error: async () => {
        await this.router.navigate([""]);
      }
    })
  }

  deletePlayer(player: Player) {
    this.apiService.deletePlayer(player.id).subscribe({
      next: () => {
        this.loadPlayers(this.team()!);
      },
      error: () => {
        this.players.set([]);
      }
    })
  }
}
