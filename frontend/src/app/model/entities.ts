
export enum Role {
  MODERATOR = 'MODERATOR',
  REDACTOR = 'REDACTOR',
  CANDIDATE = 'CANDIDATE'
}

/**
 * Bazowy interfejs dla encji eksponowanych bezpośrednio przez API.
 * Każda encja dostępna przez dedykowany endpoint MUSI rozszerzać ten interfejs.
 */
export interface MainEntity {
  id: number;
}

export interface User extends MainEntity {
  login: string;
  email: string;
  language: string;
  role: Role;
}

export interface Position extends MainEntity {
  name: string;
  description: string;
  active: boolean;
}

export const enum QuestionType {
  OPEN = 'OPEN',
  CHOICE = 'CHOICE',
  SCALE = 'SCALE',
  NUMBER = 'NUMBER'
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

export interface Test extends MainEntity {
  name: string;
  group: number;
  language: string;
  position: Position;
  questions: Question[];
}

// TODO pozostałe encje
