
export enum Role {
  MODERATOR,
  REDACTOR,
  CANDIDATE
}

export interface User {
  id: number;
  login: string;
  email: string;
  language: string;
  role: Role;
}

export interface Position {
  id: number;
  name: string;
  description: string;
  active: boolean;
}

export enum QuestionType {
  OPEN,
  CHOICE,
  SCALE,
  NUMBER
}

export interface Range {
  min: number;
  max: number;
  step: number;
}

export interface Question {
  id: number;
  type: QuestionType;
  content: string;
  availableChoices: string[];
  availableRange: Range;
}

export interface Test {
  id: number;
  name: string;
  group: number;
  language: string;
  position: Position;
  questions: Question[];
}

// TODO pozostałe encje
