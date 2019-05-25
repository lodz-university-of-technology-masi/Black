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

export enum QuestionType {
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
  availableChoices?: string[];
  availableRange?: Range;
}

export interface Test extends MainEntity {
  name: string;
  group: number;
  language: string;
  position: Position;
  questions: Question[];
}

export interface QuestionAnswer {
  id: number;
  type: QuestionType;
  choiceAnswer?: number[];
  scaleAnswer?: number;
  numberAnswer?: number;
  openAnswer?: string;
}

export interface TestAnswer extends MainEntity {
  content?: string;
  test: Test;
  user: User;
  questionAnswers: QuestionAnswer[];
}

export interface QuestionAnswerEvaluation {
  id: number;
  content?: string;
  points: number
}

export interface Evaluation extends MainEntity {
  content: string;
  testAnswer: TestAnswer;
  answersEvaluations: QuestionAnswerEvaluation[];
}

export interface UsabilityData extends MainEntity {
  ip?: string;
  browser: string;
  username?: any;
  measurementNumber?: any;
  saveTime?: Date;
  screenWidth: number;
  screenHeight: number;
  mouseClicks: number;
  timeElapsed: number;
  distance: number;
  fail: boolean;
  error?: number;
}
