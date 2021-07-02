import { NativeModules } from 'react-native'

interface ActionSheetAndroidModule {
  options: (
    title: string | null,
    message: string | null,
    cancel: string | null,
    options: string[],
    destructiveIndex: number,
    tintColor: string,
    backgroundColor: string,
    textColor: string,
    borderColor: string
  ) => Promise<number>
}

export const ActionSheetAndroidModule: ActionSheetAndroidModule = NativeModules.ActionSheetAndroid
