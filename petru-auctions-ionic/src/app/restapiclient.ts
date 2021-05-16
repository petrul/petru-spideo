import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { AuctionDto } from "./dto/AuctionDto";
import { BiddingDto } from "./dto/BiddingDto";

@Injectable()
export class RestApiClient {

  api = environment['apiUrl']
  versionUrl = this.getUrl('version')
  auctionHousesUrl = this.getUrl("auctionHouses")
  auctionsUrl = this.getUrl("auctions")
  biddingsUrl = this.getUrl("biddings")

  getUrl(verb: string) : string {
    return `${this.api}/${verb}/`
  }

  constructor(private http: HttpClient) {}

  getVersion() : Promise<string> {
    const result = new Promise<string> ((resolve, reject) => {
      this.http.get<string>(this.versionUrl).subscribe(
        data => {resolve(data)},
        err => {reject(err)}
      )
    })
    return result;
  }

  getAuctionHouses() : Promise<any> {
    const result = new Promise<any> ((resolve, reject) => {
      this.http.get<any>(this.auctionHousesUrl).subscribe(
        data => {resolve(data)},
        err => {reject(err)}
      )
    })
    return result;
  }

  async getAuctionHouse(auctionHouseId: number) {
    const url = `${this.auctionHousesUrl}${auctionHouseId}`;
    const res = await this.http.get<any>(url).toPromise();

    return res;
  }

  /**
   * returns Auction and its Biddings
   */
  async getAuction(auctionId: number): Promise<AuctionDto> {
    const url = `${this.api}/facade/auction?auctionId=${auctionId}`;
    const data = await this.http.get<AuctionDto>(url).toPromise();
    const res = new AuctionDto();
    Object.assign(res, data); // so that we have properly typed dto
    return res;
  }

  async getUserByName(login: string): Promise<any> {
    const url = `${this.api}/users/search/findByName?name=${login}`;
    let user;
    try {
      user = await this.http.get<any>(url).toPromise();
    } catch (err) {
      if (err.status !== 404) { // this really means no user so return undefined
        throw err;
      }
    }

    return user;
  }

  async postNewUser(login: string): Promise<any> {
    const url = `${this.api}/users/`;
    return await this.http.post<any>(url, { name: login }).toPromise();
  }

  async getAuctions(auctionHouseId: number): Promise<any> {
    const url = `${this.auctionHousesUrl}${auctionHouseId}/auctions`;
    return await this.http.get<any>(url).toPromise();
  }

  /* gets auctions with embedded info on seller, biddings, etc */
  async getAuctionsFromFacade(auctionHouseId: number): Promise<any> {
    const url = `${this.api}/facade/auctions?auctionHouseId=${auctionHouseId}`;
    const auctions = await this.http.get<any>(url).toPromise();
    return auctions.map( it => {
      const a = new AuctionDto();
      Object.assign(a, it);
      return a;
    });
  }

  async getBiddings(auctionId: number) {
    const url = `${this.api}/facade/biddings?auctionId=${auctionId}`;
    return await this.http.get<any>(url).toPromise();
  }

  async postNewAuctionHouse(ahname: string) {
    const url = this.auctionHousesUrl;
    return await this.http.post<any>(url, { name: ahname }).toPromise();
  }

  async postNewAuction(newauction: any) {
    const url = this.auctionsUrl;
    console.log(`posting to ${url} `, newauction);
    return await this.http.post<any>(url, newauction).toPromise();
  }

  async postNewBid(bid: BiddingDto) {
    const url = `${this.api}/facade/bid`;
    return await this.http.post<any>(url, bid).toPromise();
  }
}
