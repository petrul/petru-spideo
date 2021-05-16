import { Component, OnInit } from '@angular/core';
import { AlertController, ToastController } from '@ionic/angular';
import { Globals } from 'src/app/globals';
import { RestApiClient } from 'src/app/restapiclient';
import { Util } from 'src/app/util';

@Component({
  selector: 'app-login-signup',
  templateUrl: './login-signup.component.html',
  styleUrls: ['./login-signup.component.scss'],
})
export class LoginSignupComponent implements OnInit {

  constructor(public global: Globals,
    private alertController: AlertController,
    private apiclient: RestApiClient,
    private toast: ToastController) { }

  ngOnInit() { }

  async openDialogue() {

    const alert = await this.alertController.create({
      header: 'Login or signup',
      inputs: [
        {
          name: 'username',
          type: 'text',
          placeholder: 'Enter your user name here'
        },
      ],
      message: 'If the account does not exist, it will be created.',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
            console.log('Confirm Cancel');
          }
        }, {
          text: 'Ok',
          handler: (data) => {
            this.loginOrSignup(data.username);
          }
        }
      ]
    });

    await alert.present();
    const firstInput: any = document.querySelector('ion-alert input');
    firstInput.focus();
    return;
  }

  async loginOrSignup(login: string) {
    let user = await this.apiclient.getUserByName(login);
    console.log(`got this.apiclient.getUserByName(${login})`, user);
    if (!user) {
      user = await this.apiclient.postNewUser(login);
      console.log(`got postNewUser(${login})`, user);
      Util.presentToast(this.toast, `signed up as ${user.name}`);
    } else {
      Util.presentToast(this.toast, `logged in as ${user.name}`);
    }
    this.global.user = user;
    // this.global.login = login;
  }

}
