import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
import { MatRadioModule } from '@angular/material/radio';
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatButtonModule,
    MatExpansionModule,
    MatInputModule,
    MatStepperModule,
    MatRadioModule
  ],
  exports: [
    MatButtonModule,
    MatExpansionModule,
    MatInputModule,
    MatStepperModule,
    MatRadioModule
  ]
})
export class MaterialModule {}
