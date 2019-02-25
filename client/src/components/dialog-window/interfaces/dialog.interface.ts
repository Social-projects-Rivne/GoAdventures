import { CSSProperties } from 'react';
import { InputSettings } from './input.interface';

export interface DialogSettings {
    header: string;
    button_text: string;
    label: string;
    inputs: InputSettings[];
    inline_styles?: CSSProperties;
    handleSubmit: (event: any) => void;
}
