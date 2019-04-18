import { ChangeEvent } from "react";

export interface DropDownSettings {
  categories?: DropDownCategory[];
  categ?: string;
  customClassName?: string;
}

export interface DropDownCategory {
  id: number;
  categoryName: string;
  events: string;
}
