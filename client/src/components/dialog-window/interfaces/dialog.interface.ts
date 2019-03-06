import { CSSProperties } from 'react';
import { InputSettings } from './input.interface';

export interface DialogSettings {
    context?: any;
    header: string;
    button_text: string;
    inputs: InputSettings[];
    inline_styles?: CSSProperties;
    handleSubmit: (data: any) => Promise<boolean>;
    redirect: (data?: any) => JSX.Element;
}
