import { AlertController, ToastController } from '@ionic/angular';

export class Util {
  static async presentToast(toastCtrl: ToastController, msg: string) {
    const toast = await toastCtrl.create({
      message: msg,
      duration: 2000
    });
    await toast.present();
  }

  static async alert(alertCtrl: AlertController, msg: string) {
    const alert = await alertCtrl.create({
      header: msg
    });
    await alert.present();
  }
}
