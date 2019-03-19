export interface DropDownSettings {
    categories?: DropDownCategory[];
    categ?:string;
}

export interface DropDownCategory {
    id: number,
    categoryName: string,
    events: string
}