export default class Task {
  id: number;
  name: string;
  category: string;
  color: string;
  constructor(id: number, name: string, category: string, color: string) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.color = color;
  }
}
