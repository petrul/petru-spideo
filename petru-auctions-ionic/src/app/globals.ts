export class Globals {
  get login() { if (this.user) { return this.user.name ; } }

  user: {
    id: number;
    name: string;
  };
}
