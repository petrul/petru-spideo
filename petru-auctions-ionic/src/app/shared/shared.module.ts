/* eslint-disable @typescript-eslint/quotes */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { IonicModule } from "@ionic/angular";
import { LoginSignupComponent } from "./login-signup/login-signup.component";
import { NewAuctionComponentComponent } from "./new-auction-component/new-auction-component.component";

@NgModule({
  declarations: [LoginSignupComponent,
    NewAuctionComponentComponent],
  imports : [
    CommonModule,
    FormsModule,
    IonicModule,
  ],
  exports: [LoginSignupComponent, NewAuctionComponentComponent],
})
export class SharedModule {}
