export interface DropDownSettings {
    categories?: DropDownCategory[];
}

export interface DropDownCategory {
    id: number,
    categoryName: string,
    events: string
}