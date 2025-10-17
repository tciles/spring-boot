import {Component, inject, OnInit, signal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ApiService} from '../../core/services/api.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-add-player-page',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './add-player-page.html',
  styleUrl: './add-player-page.css'
})
export class AddPlayerPage implements OnInit {
  form = new FormGroup({
    firstName: new FormControl(""),
    lastName: new FormControl(""),
    email: new FormControl("", [Validators.email]),
  });

  errorMessage = signal("");
  readonly teamId = signal(0);

  private apiService = inject(ApiService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  public ngOnInit() {
    this.route.paramMap.subscribe((paramMap) => {
      this.teamId.set(Number.parseInt(paramMap.get("id")!));
    });
  }

  public save() {
    this.errorMessage.set("");
    const formData = this.form.value;
    const teamId = this.teamId()!

    this.apiService.createPlayer(
      formData.firstName!,
      formData.lastName!,
      formData.email!,
      teamId
    ).subscribe({
      next: async () => {
        await this.router.navigate(["/teams", teamId])
      },
      error: () => {
        this.errorMessage.set("Impossible de créer le joueur");
      }
    })
  }
}
