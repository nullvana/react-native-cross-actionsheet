var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { ActionSheetIOS, Platform } from 'react-native';
import { ActionSheetAndroid } from './ActionSheetAndroid';
import { ActionSheetAndroidModule } from './ActionSheetAndroidModule';
function androidOptions(cfg) {
    var _a, _b, _c, _d, _e;
    return __awaiter(this, void 0, void 0, function* () {
        const index = yield ActionSheetAndroidModule.options((_a = cfg.title) !== null && _a !== void 0 ? _a : null, (_b = cfg.message) !== null && _b !== void 0 ? _b : null, cfg.cancel === false ? null : ((_c = cfg.cancel) === null || _c === void 0 ? void 0 : _c.text) ? cfg.cancel.text : 'Cancel', cfg.options.map((it) => it.text), cfg.options.findIndex((it) => it.destructive), (_d = cfg.tintColor) !== null && _d !== void 0 ? _d : '#222222');
        if (index === -1) {
            if (cfg.cancel && cfg.cancel.onPress) {
                yield ((_e = cfg.cancel) === null || _e === void 0 ? void 0 : _e.onPress());
            }
        }
        else {
            yield cfg.options[index].onPress();
        }
    });
}
function iosOptions(cfg) {
    var _a;
    return __awaiter(this, void 0, void 0, function* () {
        const options = cfg.options.map((it) => it.text);
        const cancel = cfg.cancel === false ? null : ((_a = cfg.cancel) === null || _a === void 0 ? void 0 : _a.text) ? cfg.cancel.text : 'Cancel';
        return new Promise((res) => {
            ActionSheetIOS.showActionSheetWithOptions({
                title: cfg.title,
                message: cfg.message,
                options: cancel ? [...options, cancel] : options,
                destructiveButtonIndex: cfg.options.findIndex((it) => it.destructive),
                cancelButtonIndex: cancel ? options.length : undefined,
                tintColor: cfg.tintColor,
                anchor: cfg.anchor
            }, (buttonIndex) => __awaiter(this, void 0, void 0, function* () {
                var _a;
                if (cancel && buttonIndex === options.length) {
                    if (cfg.cancel && cfg.cancel.onPress) {
                        res(yield ((_a = cfg.cancel) === null || _a === void 0 ? void 0 : _a.onPress()));
                    }
                    else {
                        res();
                    }
                }
                else {
                    res(cfg.options[buttonIndex].onPress());
                }
            }));
        });
    });
}
export const ActionSheet = new (class {
    showActionSheetWithOptions(options, callback) {
        if (Platform.OS === 'android') {
            ActionSheetAndroid.showActionSheetWithOptions(options, callback);
        }
        else if (Platform.OS === 'ios') {
            ActionSheetIOS.showActionSheetWithOptions(options, callback);
        }
        else {
            throw Error('Unsupported OS. Only Android or iOS is allowed');
        }
    }
    options(options, config) {
        return __awaiter(this, void 0, void 0, function* () {
            let opt;
            if (Array.isArray(options)) {
                opt = Object.assign({ options }, config);
            }
            else {
                opt = options;
            }
            if (Platform.OS === 'android') {
                yield androidOptions(opt);
            }
            else if (Platform.OS === 'ios') {
                yield iosOptions(opt);
            }
            else {
                throw Error(`Unsupported OS: ${Platform.OS}. Only Android or iOS is allowed`);
            }
        });
    }
})();
