import { ActionSheetAndroidModule } from './ActionSheetAndroidModule';
import { Platform } from 'react-native';
export const ActionSheetAndroid = new (class {
    showActionSheetWithOptions(options, callback) {
        var _a, _b, _c, _d, _e, _f, _g;
        if (Platform.OS === 'android') {
            const optionsWithoutCancel = options.options.filter((it, index) => index !== options.cancelButtonIndex);
            let destructiveButtonIndex = (_a = options.destructiveButtonIndex) !== null && _a !== void 0 ? _a : -1;
            if (destructiveButtonIndex != null &&
                options.cancelButtonIndex != null &&
                destructiveButtonIndex > options.cancelButtonIndex) {
                destructiveButtonIndex = destructiveButtonIndex - 1;
            }
            ActionSheetAndroidModule.options((_b = options.title) !== null && _b !== void 0 ? _b : null, (_c = options.message) !== null && _c !== void 0 ? _c : null, options.cancelButtonIndex != null ? options.options[options.cancelButtonIndex] : null, optionsWithoutCancel, destructiveButtonIndex, (_d = options.tintColor) !== null && _d !== void 0 ? _d : '#222222', (_e = options.backgroundColor) !== null && _e !== void 0 ? _e : '#DDDDDD', (_f = options.textColor) !== null && _f !== void 0 ? _f : '#222222', (_g = options.borderColor) !== null && _g !== void 0 ? _g : '#DDDDDD').then((index) => {
                if (options.cancelButtonIndex != null) {
                    if (index === -1) {
                        callback(options.cancelButtonIndex);
                    }
                    else if (index >= options.cancelButtonIndex) {
                        callback(index + 1);
                    }
                    else {
                        callback(index);
                    }
                }
                else {
                    callback(index);
                }
            });
        }
    }
})();
