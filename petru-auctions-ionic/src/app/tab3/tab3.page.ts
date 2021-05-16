/* eslint-disable @angular-eslint/use-lifecycle-interface */
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertController, ToastController } from '@ionic/angular';
import { utils } from 'protractor';
import { AuctionDto } from '../dto/AuctionDto';
import { BiddingDto } from '../dto/BiddingDto';
import { Globals } from '../globals';
import { RestApiClient } from '../restapiclient';
import { RouterExtService } from '../router-ext-service';
import { Util } from '../util';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})
export class Tab3Page {

  auctionId: string;
  auction: AuctionDto;
  biddings: Array<BiddingDto> = [];
  previousUrl;

  constructor(
    private apiclient: RestApiClient,
    private route: ActivatedRoute,
    private routeExtService: RouterExtService,
    private global: Globals,
    private alertCtrl: AlertController,
    private toastCtrl: ToastController,
    ) {}

  ngOnInit() {
    this.auctionId = this.route.snapshot.paramMap.get('id');
    this.getAuction().then( _ => this.biddings = this.auction.biddings);
  }

  ionViewDidEnter() {
    this.previousUrl = this.routeExtService.previousUrl;
  }

  async getAuction(): Promise<AuctionDto> {
    if (this.auctionId) {
      const data = await this.apiclient.getAuction(Number(this.auctionId));
      this.auction = data;
      return data;
    }
  }

  async bid() {
    if (!this.global.login) {
      Util.alert(this.alertCtrl, 'You must be logged in to bid.');
      return;
    }

    const alert = await this.alertCtrl.create({
      header: 'New bid',
      inputs: [
        {
          type: 'number',
          name: 'bid',
          value: this.auction.crtPrice + 1
        }
      ],
      buttons: [
        { text: 'OK', role: 'OK',
          handler: (data) => this.doBid(data.bid)
        },
        { text: 'Cancel', role: 'Cancel'}
      ]});
      await alert.present();
  }

  async doBid(bid: number) {
    const obj = {
      date: new Date().toISOString(),
      price: bid,
      user: this.global.user,
      auctionId: this.auction.id
    };
    const biddto = new BiddingDto();
    Object.assign(biddto, obj);
    const dto = await this.apiclient.postNewBid(biddto);
    this.biddings.push(dto);
    await Util.presentToast(this.toastCtrl, `New bid ${bid} with id ${dto.id} registered for user [${this.global.login}]`);
  }
}
