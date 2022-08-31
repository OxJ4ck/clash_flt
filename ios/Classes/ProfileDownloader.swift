//
//  ProfileDownloader.swift
//  clash_flt
//
//  Created by LondonX on 2022/8/26.
//

import Foundation
import CommonCrypto

///
/// donwload profile file
/// - Returns profile file path if download success
///
func downloadProfile(url: String?, force: Bool) async -> URL? {
    if(url == nil) {
        return nil
        
    }
    let key = SHA256(string: url!)
    var profileFile = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first
    if(profileFile == nil) {
        return nil
    }
    profileFile!.appendPathComponent("profiles/\(key).yaml")
    let path = profileFile!.path
    if (!force && FileManager.default.fileExists(atPath: path)) {
        return profileFile
    }
    return await withCheckedContinuation { continuation in
        URLSession.shared.downloadTask(with: URL(string: url!)!) { (tempFileUrl, response, error) in
            if(error != nil) {
                print(error!)
            }
            if(tempFileUrl == nil) {
                continuation.resume(returning: nil)
                return
            }
            do {
                let data = try Data(contentsOf: tempFileUrl!)
                try data.write(to: profileFile!)
                continuation.resume(returning: profileFile)
            } catch {
                continuation.resume(returning: nil)
                return
            }
        }.resume()
    }
}

private func SHA256(string: String) -> String {
    let length = Int(CommonCrypto.CC_MD5_DIGEST_LENGTH)
    let messageData = string.data(using:.utf8)!
    var digestData = Data(count: length)
    
    _ = digestData.withUnsafeMutableBytes { digestBytes -> UInt8 in
        messageData.withUnsafeBytes { messageBytes -> UInt8 in
            if let messageBytesBaseAddress = messageBytes.baseAddress, let digestBytesBlindMemory = digestBytes.bindMemory(to: UInt8.self).baseAddress {
                let messageLength = CC_LONG(messageData.count)
                CC_SHA256(messageBytesBaseAddress, messageLength, digestBytesBlindMemory)
            }
            return 0
        }
    }
    let hex = digestData.map { String(format: "%02hhx", $0) }.joined()
    return hex
}