import type { Avatar } from '#ui/types'

export type UserStatus = 'subscribed' | 'unsubscribed' | 'bounced'

export interface User {
  id: number
  name: string
  email: string
  avatar?: Avatar
  status: UserStatus
  location: string
}

export interface Mail {
  id: number
  unread?: boolean
  from: User
  subject: string
  body: string
  date: string
}

export interface Member {
  name: string
  username: string
  role: 'member' | 'owner'
  avatar: Avatar
}

export interface Notification {
  id: number
  unread?: boolean
  sender: User
  body: string
  date: string
}

export type Period = 'daily' | 'weekly' | 'monthly'

export interface Range {
  start: Date
  end: Date
}



export interface Patient {
  id: number
  id_index: number
  citizenID: number
  fullName: string
  // location: string
  timestamp: number
}


export interface Patient_monitoring {
  citizenID: number
  fullName: string
  // location: string
  status: string
}


export interface AbnormalHealthRecord{
  citizenID: number
  timestamp: string
  comment: string
}



export interface User {
  id: string;
  email: string;
  password: string;
  roles: string[];
}

export type UserWithoutPassword = Omit<User, "password">;
