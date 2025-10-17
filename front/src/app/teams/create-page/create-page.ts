import {Component, inject, signal} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {ApiService} from '../../core/services/api.service';

@Component({
  selector: 'app-create-page',
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './create-page.html',
  styleUrl: './create-page.css'
})
export class CreatePage {
    name = new FormControl('');
    errorMessage = signal("");

    private apiService = inject(ApiService);
    private router = inject(Router);


    public save() {
      const name = this.name.value!;

      this.apiService.createTeam(name).subscribe({
        next: async (v) => {
          await this.router.navigate([""]);
        },
        error: (e) => {
          this.errorMessage.set("Erreur lors de la création");
        },
        complete: () => {}
      });
    }
}
