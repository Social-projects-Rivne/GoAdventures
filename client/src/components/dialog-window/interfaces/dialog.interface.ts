import { CSSProperties } from 'react';
import { InputSettings } from './input.interface';

export interface DialogSettings {
    header: string;
    label: string;
    inputs: InputSettings[];
    inline_styles?: CSSProperties;
    handleSubmit?: (event: Event) => {};
}
